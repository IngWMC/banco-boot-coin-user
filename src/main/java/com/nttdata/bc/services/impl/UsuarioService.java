package com.nttdata.bc.services.impl;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import com.nttdata.bc.documents.Usuario;
import com.nttdata.bc.exceptions.BadRequestException;
import com.nttdata.bc.exceptions.NotFoundException;
import com.nttdata.bc.repositories.UsuarioRepository;
import com.nttdata.bc.services.IUsuarioService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioService implements IUsuarioService {
    @Inject
    Logger LOGGER;

    @Inject
    UsuarioRepository repository;

    @Override
    public Uni<Usuario> insert(Usuario obj) {
        LOGGER.info("ENTRO");
        return this.validarDocumentoIdentidad(obj.getDocumentoIdentidad())
                .flatMap((v) -> {
                    obj.setEsActivo(true);
                    obj.setFechaCreacion(LocalDateTime.now());
                    return this.repository.persist(obj);
                });

    }

    @Override
    public Uni<Usuario> update(Usuario obj) {
        return this.repository.findById(obj.getId())
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("El usuario con el id: " + obj.getId() + ", no existe."))
                .flatMap((v) -> {
                    obj.setFechaModificacion(LocalDateTime.now());
                    return this.repository.update(obj);
                });
    }

    @Override
    public Multi<Usuario> listAll() {
        return this.repository.listAll()
                .onItem()
                .<Usuario>disjoint()
                .map(usario -> usario);
    }

    @Override
    public Uni<Usuario> findById(ObjectId id) {
        return this.repository.findById(id);
    }

    @Override
    public Uni<Void> deleteById(ObjectId id) {
        return this.repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("El usario con el id: " + id + ", no existe."))
                .flatMap((usario) -> {
                    usario.setEsActivo(false);
                    usario.setFechaModificacion(LocalDateTime.now());
                    return this.repository.update(usario);
                })
                .replaceWithVoid();

    }

    private Uni<Void> validarDocumentoIdentidad(String documentoIdentidad) {
        return this.repository.list("documentoIdentidad",
                documentoIdentidad)
                .onItem()
                .<Usuario>disjoint()
                .toUni()
                .onItem()
                .ifNotNull()
                .failWith(() -> new BadRequestException(
                        "Ya existe un usuario con el documento identidad: " + documentoIdentidad + "."))
                .replaceWithVoid();
    }

}
