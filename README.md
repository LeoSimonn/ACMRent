# ACMERent - Sistema de Locação de Automóveis

## Sobre o Projeto
Sistema de gerenciamento de locação de automóveis desenvolvido com Spring Boot.

## Configuração do Ambiente
- Java 17 ou superior
- Maven 3.x
- Banco de dados H2 (embutido)

## Como Executar
1. Clone o repositório
2. Execute o comando: `mvn spring-boot:run`
3. A aplicação estará disponível em: `http://localhost:8080`

## Banco de Dados (H2)
- Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./acmerentdb`
- Usuário: `sa`
- Senha: `password`

## Endpoints Disponíveis

### Automóveis

#### Listar Todos os Automóveis
```
GET http://localhost:8080/acmerent/listaautomoveis
```
Retorna a lista de todos os automóveis cadastrados.

#### Validar Automóvel
```
POST http://localhost:8080/acmerent/validaautomovel
```
Corpo da requisição:
```json
{
    "id": 1
}
```
Verifica se um automóvel está disponível para locação.

#### Atualizar Estado do Automóvel
```
POST http://localhost:8080/acmerent/atendimento/atualizaautomovel/{id}/estado/{status}
```
Exemplo:
```
POST http://localhost:8080/acmerent/atendimento/atualizaautomovel/1/estado/RENTED
```
Status possíveis: `AVAILABLE`, `RENTED`, `REMOVED`

### Clientes

#### Listar Todos os Clientes
```
GET http://localhost:8080/acmerent/listaclientes
```
Retorna a lista de todos os clientes cadastrados.

#### Consultar Cliente por Código
```
GET http://localhost:8080/acmerent/consultacliente?codigo={codigo}
```
Exemplo:
```
GET http://localhost:8080/acmerent/consultacliente?codigo=1
```

### Locações

#### Listar Todas as Locações
```
GET http://localhost:8080/acmerent/listalocacoes
```
Retorna a lista de todas as locações realizadas.

#### Cadastrar Nova Locação
```
POST http://localhost:8080/acmerent/atendimento/cadlocacao
```
Corpo da requisição:
```json
{
    "numero": 1,
    "clienteId": 1,
    "automovelId": 1,
    "dias": 5,
    "datainicial": "2024-05-04",
    "valordiaria": 100.00
}
```

#### Finalizar Locação
```
POST http://localhost:8080/acmerent/atendimento/finalizalocacao
```
Corpo da requisição:
```json
{
    "numero": 1
}
```
Retorna `true` se a locação foi finalizada com sucesso, `false` caso contrário.

## Dados Iniciais
A aplicação é inicializada com alguns dados de exemplo:
- 3 clientes
- 10 automóveis
- 2 locações

## Exemplos de Uso com cURL

### Listar Automóveis
```bash
curl http://localhost:8080/acmerent/listaautomoveis
```

### Cadastrar Locação
```bash
curl -X POST http://localhost:8080/acmerent/atendimento/cadlocacao \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 3,
    "clienteId": 1,
    "automovelId": 3,
    "dias": 5,
    "datainicial": "2024-05-04",
    "valordiaria": 100.00
  }'
```

### Validar Automóvel
```bash
curl -X POST http://localhost:8080/acmerent/validaautomovel \
  -H "Content-Type: application/json" \
  -d '{"id": 1}'
```

### Finalizar Locação
```bash
curl -X POST http://localhost:8080/acmerent/atendimento/finalizalocacao \
  -H "Content-Type: application/json" \
  -d '{"numero": 1}'
``` 