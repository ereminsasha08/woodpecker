package org.woodpecker.repository.model;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}