package com.nttdata.bc.repositories;

import com.nttdata.bc.documents.Usuario;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements ReactivePanacheMongoRepository<Usuario> {

}
