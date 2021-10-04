CREATE TABLE autores(
	id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    data_nascimento DATE NOT NULL,
    mini_curriculo VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
)