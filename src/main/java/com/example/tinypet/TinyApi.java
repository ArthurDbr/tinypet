/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tinypet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiIssuerAudience;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.cloud.datastore.KeyFactory;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;

/**
 * The TinyApi API which Endpoints will be exposing.
 */
// [START tiny_api_annotation]
@Api(name = "tinyapi", version = "v1", namespace = @ApiNamespace(ownerDomain = "tinypet.example.com", ownerName = "tinypet.example.com", packagePath = ""),
		// [START_EXCLUDE]
		issuers = {
				@ApiIssuer(name = "firebase", issuer = "https://securetoken.google.com/tinypet2", jwksUri = "https://www.googleapis.com/service_accounts/v1/metadata/x509/securetoken@system"
						+ ".gserviceaccount.com") }
// [END_EXCLUDE]
)
// [END tiny_api_annotation]

public class TinyApi {


	@ApiMethod(name = "getPetitions", httpMethod = HttpMethod.GET, path = "petitions")
	public Collection<Petition> getPetitions() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Petition");
		PreparedQuery pq = datastore.prepare(q);
		Collection<Petition> petitions = new ArrayList();
		for (Entity result : pq.asIterable()) {
			Key id = (Key) result.getKey();
			Petition pet = new Petition(id.getId(), (String) result.getProperty("title"), (String) result.getProperty("description"),
					(String) result.getProperty("creator"), (Long) result.getProperty("votes"), (String) result.getProperty("idCreator"));
			petitions.add(pet);
		}
		return petitions;
	}
	
	@ApiMethod(name = "getPetitionById", path = "petitions/{id}")
	public Petition getPetitionById(Petition petition, @Named("id") Long id) {
		com.googlecode.objectify.Key key = com.googlecode.objectify.Key.create(Petition.class, id);
		Petition pet = (Petition) ofy().load().key(key).now();
		return pet;
	}
	
	@ApiMethod(name = "getVotedPetitionsByUser", httpMethod = HttpMethod.GET, path = "petitions/vote/user/{id}")
	public Collection<Petition> getVotedPetitionsByUser(@Named("id") String id) {
		List<Signature> signs = ofy().load().type(Signature.class).filter("idUser =", id).list();
		List<Petition> petitions = new ArrayList();
		for(Signature sign : signs) {
			com.googlecode.objectify.Key key = com.googlecode.objectify.Key.create(Petition.class, Long.parseLong(sign.getIdPetition()));
			Petition pet = (Petition) ofy().load().key(key).now();
			petitions.add(pet);
		}
		return petitions;
	}
	
	@ApiMethod(name = "getPetitionsByCreator", httpMethod = HttpMethod.GET, path = "petitions/user/{id}")
	public Collection<Petition> getPetitionsByCreator(@Named("id") String id) {
		List<Petition> petitions = ofy().load().type(Petition.class).filter("idCreator =", id).list();
		return petitions;
	}
	

	@ApiMethod(name = "postPetition", httpMethod = HttpMethod.POST, path = "petitions")
	public Petition postPetition(Petition petition, @Named("n") @Nullable Integer n) {
		Entity pet = new Entity("Petition");
		pet.setProperty("title", petition.getTitle());
		pet.setProperty("description", petition.getDescription());
		pet.setProperty("creator", petition.getCreator());
		pet.setProperty("idCreator", petition.getIdCreator());
		pet.setProperty("votes", 0);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key petId = datastore.put(pet); 
		petition.setId(petId.getId());
		
		com.googlecode.objectify.Key key = com.googlecode.objectify.Key.create(Aggregate.class, "numberOfPetitions");
		Aggregate agg = (Aggregate) ofy().load().key(key).now();
		agg.setValue(agg.getValue()+1);
		ofy().save().entity(agg); 
		return petition;
	}

	@ApiMethod(name = "votePetition", httpMethod = HttpMethod.POST, path = "petitions/vote/{idPetition}/{idUser}")
	public Petition postVotePetition(Petition petition, @Named("idPetition") String idPetition, @Named("idUser") String idUser) {
		
		com.googlecode.objectify.Key key = com.googlecode.objectify.Key.create(Petition.class, Long.parseLong(idPetition));
		Petition pet = (Petition) ofy().load().key(key).now();
		pet.setVotes(pet.getVotes()+1);
		ofy().save().entity(pet);
		
		Entity sign = new Entity("Signature");
		sign.setProperty("idUser", idUser);
		sign.setProperty("idPetition", idPetition);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key signId = datastore.put(sign); // Save the Entity
		
		com.googlecode.objectify.Key keyAgg = com.googlecode.objectify.Key.create(Aggregate.class, "numberOfVotes");
		Aggregate agg = (Aggregate) ofy().load().key(keyAgg).now();
		agg.setValue(agg.getValue()+1);
		ofy().save().entity(agg);         // asynchronous

		return pet;
	}
	
	@ApiMethod(name = "getAggregates", httpMethod = HttpMethod.GET, path = "aggregates")
	public Collection<Aggregate> getAggregates() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Aggregate");
		PreparedQuery pq = datastore.prepare(q);
		Collection<Aggregate> aggregates = new ArrayList();
		for (Entity result : pq.asIterable()) {
			Key key = (Key) result.getKey();
			Aggregate agg = new Aggregate(key.getName() + "", (Long)result.getProperty("value"));
			aggregates.add(agg);
		}
		return aggregates;
	}




	
}
