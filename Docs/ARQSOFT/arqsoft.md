**ASR-1 - Variabilidade do SGBD**
|     Element    | Statement                                                                        |
|:-------------- |:---------------------------------------------------------------------------------|
|   Stimulus     | A aplicação LMS não suporta a variabilidade do uso de vários tipos de sistemas de gestão de bases de dados                                                                                               | 
|Stimulus Source | O Product Owner precisa de utilizar diversos sistemas de gestão de bases de dados|
|  Environment   | Manter vários branches/projetos para utilizar diversos sistemas de bases de dados não é sustentável podendo levar a código duplicado e, assim aumentando a manutenibilidade e o potencial de erros                                                                                | 
|    Artifact    | Aplicação LMS em particular aos repositórios                                     | 
|    Response    | Uma solução baseada em configurações deverá ser implementada para apenas uma branch/projeto existir. Será utilizado a anotação @Profile do Spring Boot para determinar qual será a SGBD ativa.                                                                                  | 
|Response Measure| A alteração da configuração do SGBD não deverá exceder o tempo de 30mins         |

**Technical Memo**
Issue:
Os diferentes clientes podem trabalhar com diferentes SGBDs, como por exemplo SQL, 
MongoDB e Redis

Summary of solution: 
Adoção de uma solução baseada em configurações que determinam qual SGDB usar. Será usada a anotação @Profile do Spring Boot para determinar qual SGBD usar. 

Factors:
Uso de Clean Architecture que permite a abstração do repositório

Solution:
Implementação de uma solução derivada de configurações que seleciona o SGBD ativo via @Profile do Spring Boot
Abstração da lógica da persistência através de interfaces comuns, conseguindo assim a implementação de várias SGBD sem duplicação de código

Motivation: 
Adicionar variabilidade na persistência dos dados permitindo assim o aumento da manutenibilidade. 

Alternatives: 
Adicionar as condições if-else na persistência dos dados.


**ASR-2 - ID Generation**
| Element        | Statement                                                                                                                |
|:-------------- |:-------------------------------------------------------------------------------------------------------------------------|
|   Stimulus     | O requisito de gerar IDs únicos para as diferentes entidades                                                             | 
|Stimulus Source | O Product Owner quer que a aplicação crie IDs para as diversas entidades para que estas mantenham a integridade dos dados|
|  Environment   | A geração de IDs deve suportar a implementação de diversas abordagens                                                    | 
|    Artifact    | A aplicação LMS em particular nas classes de Domínio                                                                     | 
|    Response    | Criação de um novo campo nas entidades para receberem este novo ID gerado e também uma interface para a geração dos IDs para que seja possível fazer a implementação de várias abordagens. Será utilizado a anotação @Profile do Spring Boot para determinar qual será a geração de IDs ativa.                             | 
|Response Measure| A alteração da geração do ID não deve exceder o tempo de 30mins                                                          |

**Technical Memo**
Issue:
A aplicação LMS necessita de criar IDs únicos para as diferentes entidades, garantindo assim que tanto o domínio, tanto as data models mantenham a integridade dos dados 

Summary of solution:
Criação de um mecanismo de geração de IDs baseado numa interface, permitindo assim múltiplas abordagens. A abordagem ativa será determinada pelo @Profile do Spring Boot

Factors:
Uso de Dependency Injection para flexibilidade

Solution:
Adicionar um novo campo nas diversas entidades para receber o ID criado.
Criação de uma interface para a geração dos IDs podendo assim determinar as diversas implementações possíveis. A implementação ativa será determinada pelo @Profile do Spring Boot

Motivation:
Garantir a integridade dos dados, e a variabilidade do sistema de geração dos IDs

Alternatives:
Implementar a lógica da geração dos IDs diretamente nas entidades com a condição if-else para as diferentes abordagens


**ASR-3 - API externo para procurar ISBN**
|   Element      | Statement                                                                                             |
|:-------------- |:------------------------------------------------------------------------------------------------------|
|   Stimulus     | O requisito de procurar o ISBN através do título de um livro                                          | 
|Stimulus Source | O Product Owner quer que a aplicação chame uma API externa que procure o ISBN de um determinado livro |
|  Environment   | A função da busca do ISBN através de um API externa deve suportar a implementação de várias abordagens| 
|    Artifact    | A aplicação LMS                                                                                       | 
|    Response    | A criação de uma interface para que seja possível a implementação para diferentes APIs externas. Será utilizada a anotação Profile do Spring Boot para determinar qual será a abordagem utilizada                                                                                                      | 
|Response Measure| A alteração da abordagem escolhida não deverá exceder o tempo de 30mins                               |

**Technical Memo**
Issue:
A aplicação LMS permite a pesquisa do ISBN através do título de um livro através de uma API externa

Summary of solution: 
Criação de uma interface que permita diversas implementações. A implementação ativa será determinada pelo @Profile do Spring Boot

Factors:
Uso de Dependency Injection para flexibilidade

Solution:
Criação da interface para permitir a implementação de diversas APIs externas. A implementação ativa será determinada pelo @Profile do Spring Boot

Motivation: 
Garantir a variabilidade de qual API externa usar

Alternatives: 
Ter apenas uma implementação fixa de uma API