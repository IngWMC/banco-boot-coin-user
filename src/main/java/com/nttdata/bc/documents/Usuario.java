package com.nttdata.bc.documents;

import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@MongoEntity(collection = "usuarios")
public class Usuario {
    private ObjectId id;

    @NotEmpty(message = "El campo nombre es requerido.")
    private String nombre;

    @NotEmpty(message = "El campo tipoDocumentoIdentidad es requerido.")
    @Pattern(regexp = "^DNI$|^CEX$|^PAS$", message = "El campo tipoDocumentoIdentidad debe tener uno de estos valores: [DNI, CEX, PAS].")
    @BsonProperty("tipoDocumentoIdentidad")
    private String tipoDocumentoIdentidad;

    @NotEmpty(message = "El campo documentoIdentidad es requerido.")
    @BsonProperty("documentoIdentidad")
    private String documentoIdentidad;

    @NotEmpty(message = "El campo celular es requerido.")
    @BsonProperty("celular")
    private String celular;

    @NotEmpty(message = "El campo correo es requerido.")
    @Email
    private String correo;

    @Min(value = 1, message = "El campo cantidadBootCoin debe tener un valor mínimo de '1'.")
    @Digits(integer = 10, fraction = 0, message = "El campo cantidadBootCoin debe es número positivo.")
    @NotNull(message = "El campo cantidadBootCoin es requerido.")
    private int cantidadBootCoin;

    @BsonProperty("esActivo")
    private Boolean esActivo;

    @BsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;

    @BsonProperty("fechaModificacion")
    private LocalDateTime fechaModificacion;
}
