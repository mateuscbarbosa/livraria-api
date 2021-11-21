# Projeto Livraria
![Badge em Desenvolvimento](https://img.shields.io/badge/Status-Em%20Desenvolvimento-success?style=for-the-badge)
![Badge Front-End](https://img.shields.io/badge/Front--End-N%C3%A3o%20Desenvolvido-critical?style=for-the-badge)
![Badge envio de E-mails](https://img.shields.io/badge/E--mails-Funcionando-blue?style=for-the-badge)

## Descrição do Projeto
Projeto desenvolvido como desafio ao longo de todo Bootcamp de Java 2021 da Alura.
O objetivo era criar uma API Rest com cadastro de *Usuários*, *Autores*, e *Livros*, para uma livraria fictícia. Cada um possuindo suas próprias regras para o funcionamento do sistema.

## Funcionalidades
`Usuários`
- Listar: Lista todos os **usuários** do sistema;
- Cadastrar: Cadastra um novo **usuário** para acessar o sistema. É necessário ter um e-mail válido para receber a senha gerada automáticamente;
- Atualizar: Atualiza um **usuário** a partir do ID e altera suas informações;
- Remover: Remove um **usuário** a partir do ID;
- Detalhar: Detalha um **usuário** a partir do ID;
- Perfis de Usuário: Para ter acesso a todas as funções de usuário, no cadastro é necessário informar o ID de perfil *1 = ADMIN*, outros usuários podem usar o perfil *2 = COMUM* para as funções a seguir.

`Autores`
- Listar: Lista todos os **autores** do sistema;
- Cadastrar: Cadastra um novo **autor** para que *livros* possam ser relacionados a ele;
- Atualizar: Atualiza um **autor** com o ID selecionado;
- Remover: Remove um **autor** com o ID selecionado, se ele não tiver nenhum livro registrado no sistema;
- Detalhar: Detalha um **autor** com o ID selecionado;

`Livros`
- Listar: Lista todos os **livros** do sistema;
- Cadastrar: Cadastra um novo **livro** para um dos *autores* previamente registrados no sistema;
- Atualizar: Atualiza um **livro** com o ID selecionado;
- Remover: Remove um **livro** com o ID selecionado;
- Detalhar: Detalha um **livro** com o ID selecionado;

`Relatório`
- Listar: Mostra uma relação da quantidade de ***livros*** por ***autor*** e a porcentagem da quantidade de livros no sistema por autor.

## Acesso ao Sistema
Por não possuir Front-End o único meio de acessar o sistema é pela documentação criada pelo Swagger atráves desse [link](https://livraria-api-m.herokuapp.com/swagger-ui.html#/); Por lá todas as funções são explicadas.
Para obejtivos de teste um usuário Mestre foi criado
Login: teste
Senha: ~~123456~~
É só logar, criar seu usuário e testar o sistema!

## Abrir e Rodar
Para abrir o código na sua máquina é necessário ter uma IDE de `Java` instalada, e o JDK 11 ou maior.
1. O Lombok precisa estar instalado;
2. As dependências do Maven precisam ser baixadas;
3. O banco de dados `MySql` precisa estar instalado, e o banco *livraria* criado;

## Tecnologias Usadas
* `Java`
* `SpringBoot`
* `SpringMVC`
* `Maven`
* `MySql`
* `Lombok`
* `FlyWay`
* `Swagger`
* `JJWT`
* `BCryptPasswordEncoder`
* `JavaMailSender`
* `Docker`
* `Docker-Compose`
* `Heroku`
* `Git e GitHub`
* `JUnit5`
## Autor
[Mateus C Barbosa](https://github.com/mateuscbarbosa)

## Agrecimentos
Agradecimento especial para **Alura** e os professores **Rita Cury** e **Rodrigo Caneppele** pela didática e auxílo em todo esse processo de aprendizagem.
