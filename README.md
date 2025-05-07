<h2 align="center">Smart Manage: Sistema para Gerenciamento de Vendas</h2>
<br>

## Sumário
- [Visão geral](#visao-geral)
- [Diagrama de classes](#diagrama-de-classes)
- [Requisitos do produto](#requisitos-do-produto)

<br>

## Visão geral
Repositório dedicado ao desenvolvimento do trabalho da disciplina Técnicas de Programação em Plataformas Emergentes do 1º semestre de 2025.

Para este trabalho, foi selecionado um projeto previamente desenvolvido na disciplina de Orientação a Objetos, com o objetivo de criar uma nova versão reestruturada, incorporando novas tecnologias e a aplicação de boas práticas de engenharia de software.

O projeto original está disponível neste [repositório](https://gitlab.com/liander/ep1), enquanto a proposta completa de desenvolvimento pode ser consultada [aqui](https://gitlab.com/oofga/eps/eps_2019_2/ep1).

<br>

## Diagrama de classes

<img src="./images/uml.svg">
<figcaption align="center" >Figura 1 - Diagrama de Classes. Fonte: Bruna Lima </figcaption>

<br>

## Requisitos do produto

### User role

- **Administrador**: Usuário que gerencia e supervisiona todos os aspectos do sistema.
- **Funcionário**: Usuário que interage com o sistema para gerenciar e realizar tarefas diárias, como o controle de estoque, atendimento a clientes e processamento de vendas.

| Feature | User story |
|---------|------------|
| **[FEAT01]** Gestão de usuários | **[US01]** Como administrador, quero ser capaz de cadastrar um funcionário para que ele possa acessar e utilizar o sistema. <br> **[US02]** Como administrador, quero ser capaz de atualizar o registro um funcionário para manter as informações atualizadas. <br> **[US03]** Como administrador, quero ser capaz de visualizar o registro um funcionário para consultar suas informações. <br> **[US04]** Como administrador, quero ser capaz de deletar o registro um funcionário para remover usuários inativos. <br> **[US05]** Como funcionário, quero ser capaz de cadastrar um cliente para realizar vendas. <br> **[US06]** Como funcionário, quero ser capaz de atualizar o registro de um cliente para manter as informações atualizadas. <br> **[US07]** Como funcionário, quero ser capaz de visualizar o registro de um cliente para consultar suas informações. <br> **[US08]** Como funcionário, quero ser capaz de deletar o registro de um cliente para remover usuários inativos. |
| **[FEAT02]** Gestão de estoque | **[US09]** Como funcionário, quero ser capaz de cadastrar uma nova categoria para classificar os produtos. <br> **[US10]** Como funcionário, quero ser capaz de atualizar o registro uma categoria para manter as informações atualizadas. <br> **[US11]** Como funcionário, quero ser capaz de visualizar todas as categoria para consultar suas informações. <br> **[US12]** Como funcionário, quero ser capaz de cadastrar um produto para adicionar um novo item ao estoque. <br> **[US13]** Como funcionário, quero ser capaz de atualizar o registro de um produto para manter as informações atualizadas. <br> **[US14]** Como funcionário, quero ser capaz de visualizar o registro de um produto para consultar suas informações. <br> **[US15]** Como funcionário, quero ser capaz de visualizar uma lista de todos os produtos, filtrar por categoria e buscar por nome para encontrar rapidamente os itens desejados. |
| **[FEAT03]** Gestão de vendas | **[US16]** Como funcionário, quero ser capaz de adicionar itens ao carrinho de um cliente para preparar o pedido. <br> **[US17]** Como funcionário, quero ser capaz de atualizar a quantidade de itens do carrinho de um cliente para manter os itens do pedido atualizados. <br> **[US18]** Como funcionário, quero ser capaz de visualizar o carrinho de um cliente para revisar os itens adicionados. <br> **[US19]** Como funcionário, quero ser capaz de esvaziar o carrinho de um cliente para remover todos os itens. <br> **[US20]** Como funcionário, quero ser capaz de deletar um produto do carrinho para manter o pedido atualizado. <br> **[US21]** Como funcionário, quero ser capaz de cadastrar o pedido para registrar a compra de um cliente. <br> **[US22]** Como funcionário, quero ser capaz de visualizar o pedido para revisar os itens antes de finalizar a venda ou consultar seu status. <br>  **[US23]** Como funcionário, quero ser capaz de visualizar o histórico de pedidos para consultar vendas anteriores. <br> **[US4]** Como funcionário, quero ser capaz de deletar um pedido para remover registros de pedidos inválidos ou cancelados. |