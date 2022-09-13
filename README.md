# PostCodeSearch
O objetivo deste projeto é baixar apenas uma vez a lista de códigos postais existente num ficheiro CSV e salvar no diretorio. Posteriormente apresentar essa lista de codigos postais com possibilidade de pesquisar em um banco de dados local.

Foi utilizado para construção deste projeto a arquitetura cleancode + mvvm, assim como injeção de depedencia com Hilt e bando de dados com ROM

View <- ViewModel <- Repository

## View  
Foi contruida uma custom view para lidar com o comportamento de busca e botão de cancelar, além de todas as views estarem estilizadas, facilitando mudanças visuais e possivel tokenização.  

**CancelableEditText**  
![Captura de Tela 2022-09-13 às 09 49 34](https://user-images.githubusercontent.com/58302592/189905367-8e72bd11-a046-48eb-9b8b-75f53272739b.png)
~~~kotlin
  private fun editTextSetup() {
        editText.doAfterTextChanged { text ->
            onAfterTextChanged?.onAfterTextChanged(text.toString())
            //Aqui verifico se existe um texto então exibo o botão ou não
            cancelButton.visibility = when (text.isNullOrBlank()) {
                true -> View.GONE
                false -> View.VISIBLE
            }
        }
    }
~~~

## ViewModel  
A responsabilidade por puxar os dados e inserir no livedata ficou por parte da viewmodel, utilizando coroutines do kotlin com viewModelScope podemos puxar dados de forma assincrona diretamente do repositorio

~~~kotlin
    viewModelScope.launch {
        _addressesState.postValue(CallStateResult.Loading)
        kotlin.runCatching { repository.getAddressFromApi() }
            .onSuccess { result ->
                _addressesState.postValue(CallStateResult.OnRemoteAddressFileReceived(result))
            }
            .onFailure { error ->
                _addressesState.postValue(
                    CallStateResult.Error(error.message)
                )
            }
    }
~~~

## Repository  
Ficou responsavel por fazer o download do arquivo e salvar na mémoria, posteriormete com o ROM foi criado um banco de dados que é controlado pelo repositorio
~~~kotlin
  interface AddressRepository {
      suspend fun getAddressFromApi(): ResponseBody
      suspend fun getAddressesFromLocalDatabase(): List<AddressData>
      suspend fun saveAddresses(list: List<AddressData>)
      suspend fun searchForAddress(queryString: String): List<AddressData>
  }
~~~

## Injection  
Para facilitar a injeção de dependencia foi utilizado o Hilt, que cria instancias unicas do Retrofit, Repositorio do ROM e Repositorio do projeto

~~~kotlin
  @Module
  @InstallIn(SingletonComponent::class)
  object AppModule {
  
      @Provides
      @Singleton
      fun provideGitHubApi(): GitHubApi {
          //retroft implementation
      }

      @Provides
      @Singleton
      fun provideAddressRepository(
          api: GitHubApi,
          db: AddressLocalDataBase
      ): AddressRepository {
          //repository implemenantion
      }

      @Provides
      @Singleton
      fun provideAddressDatabase(app: Application): AddressLocalDataBase {
         //room impleentation
      }
  }
~~~
