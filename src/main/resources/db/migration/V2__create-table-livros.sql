CREATE TABLE livros(
	id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(250) NOT NULL,
    data_lancamento DATE NOT NULL,
    numero_paginas INT,
    id_autor BIGINT,
    FOREIGN KEY (id_autor) REFERENCES autores (id),
    PRIMARY KEY (id)
);