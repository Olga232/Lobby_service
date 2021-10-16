package org.example.lobby.domain.modelTransformer;

public interface ModelTransformer<E, D> {

  D asDto(E entity);
}
