CREATE TABLE usuarios(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    login VARCHAR(200) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE perfis(
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE perfis_usuarios(
	usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY(usuario_id, perfil_id),
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY(perfil_id) REFERENCES perfis(id)
);

INSERT INTO perfis VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_COMUM');