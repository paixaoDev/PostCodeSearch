# PostCodeSearch
O objetivo deste projeto é baixar apenas uma vez a lista de códigos postais existente num ficheiro CSV e salvar no diretorio. Posteriormente apresentar essa lista de codigos postais com possibilidade de pesquisar em um banco de dados local.

Foi utilizado para construção deste projeto a arquitetura cleancode + mvvm, assim como injeção de depedencia com Hilt e bando de dados com ROM

View <- ViewModel <- Repository

View
Foi contruida uma custom view para lidar com o comportamento de busca e botão de cancelar, além de todas as views estarem estilizadas, facilitando mudanças visuais e possivel tokenização.

ViewModel
A responsabilidade por puxar os dados e inserir no livedata ficou por parte da viewmodel, utilizando coroutines do kotlin com viewModelScope podemos puxar dados de forma assincrona diretamente do repositorio

Repository
Ficou responsavel por fazer o download do arquivo e salvar na mémoria, posteriormete com o ROM foi criado um banco de dados que é controlado pelo repositorio

Injection
Para facilitar a injeção de dependencia foi utilizado o Hilt, que cria instancias unicas do Retrofit, Repositorio do ROM e Repositorio do projeto
