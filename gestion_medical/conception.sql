CREATE DATABASE gestion_medicale;
\c gestion_medicale;
CREATE TABLE patient (
    id_patient INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20) UNIQUE,
    date_naissance DATE NOT NULL,
    sexe VARCHAR(10) NOT NULL
        CHECK (sexe IN ('Masculin', 'Feminin')),
    adresse TEXT,
    email VARCHAR(150) UNIQUE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (date_naissance <= CURRENT_DATE)
);
