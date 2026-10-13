package edu.dosw.wok.core.validators;

import edu.dosw.wok.core.exceptions.ConflictoException;
import edu.dosw.wok.persistence.repositories.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Implementación del Validator de Plato (ver explicación en IPlatoValidator)
@Component
@RequiredArgsConstructor   // Lombok: constructor con los campos final -> Spring inyecta el repositorio
public class PlatoValidator implements IPlatoValidator {

    private final PlatoRepository platoRepository;

    @Override
    public void validarNombreUnico(String nombre) {
        // Con BD ya no se cargan todos los platos en memoria: se pregunta a Mongo
        // directamente (S09 diapo 44, "¿Por qué el Validator ya no recibe la colección?")
        if (platoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new ConflictoException("Ya existe un plato con el nombre '" + nombre + "'");
        }
    }
}
