CREATE TABLE livros(
	id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(250) NOT NULL,
    data_lancamento DATE NOT NULL,
    numero_paginas INT,
    autor_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES autores (id),
    PRIMARY KEY (id)
);