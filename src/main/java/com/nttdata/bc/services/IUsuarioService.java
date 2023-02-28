package com.nttdata.bc.services;

import org.bson.types.ObjectId;

import com.nttdata.bc.documents.Usuario;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface IUsuarioService {
    Uni<Usuario> insert(Usuario obj);

    Uni<Usuario> update(Usuario obj);

    Multi<Usuario> listAll();

    Uni<Usuario> findById(ObjectId id);

    Uni<Void> deleteById(ObjectId id);
}
