package com.example.srodenas.example_with_catalogs.ui.viewmodel.users

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recu_app.domain.users.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel (): ViewModel() {
    val repositoryUsers = RepositoryUsers.repo  //Aquí tengo la instancia del repositorio
    val useCaseLogin = UseCaseLogin(repositoryUsers)
    val useCaseRegisterLogin = UseCaseRegisterLogin(repositoryUsers)
    val register = MutableLiveData<Boolean>()

    var isLogginPreferencesLiveData = MutableLiveData<Boolean>(false)
    var usersLiveData= MutableLiveData<MutableList<User>>()
    var posNewUserlLiveData = MutableLiveData<Int>()
    var posDeleteHotelLiveDate = MutableLiveData<Int>()
    lateinit var context: Context


    /*
        - Esta función, es la primera que ejecuta desde el Activity, para saltarse el paso del
        logueo, en el caso de que ya se haya logueado y existan sus preferencias almacenadas.

        - Si isLogginPreferences es falso, es porque no has iniciado nunca sesión, por tanto
        se le debe de mostrar el Activity del Login para que pueda loguearse. Al ser falso, no
        notifica al LiveData.

        - Si isLogginPreferences es true, es porque se han encontrado las preferencias compartidas
        de ese usuario y por tanto nos dice que ya se loguéo en una sesión anterior. En este caso,
        se crea el Perfil del usuario logueado y notificamos al LiveData para que al observar un cambio
        en dicho LiveData, actúe.
         */
    fun setContext(_context : Context) {
        context = _context
        val isLogginPreferences = isUserLoogedInShared()//Comprobamos si tiene preferencias.
        if (isLogginPreferences){
            Profile.profile.setUser(getUser())  //Creamos el Perfil del usuario
            isLogginPreferencesLiveData.value =  isLogginPreferences  //Notificamos a la UI
        }

    }




    /*
        Método que comprueba si el usuario se ha loguado correctamente.
        1.- La consulta de acceso a datos asíncrona, la haremos en un hilo diferente al principal. Bien utilizar el de E/S.
        2.- Las actualizaciones, las haremos en el mismo hilo principal. Todo lo que tenga que ver con LiveData.
 */
    fun isLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {//esta corrutina, se ejecuta en el hilo de E/S, no en el principal.
            val user: User? = null
            if (!email.isEmpty() && !password.isEmpty()) {
                val user = useCaseLogin.login(email, password) //invocamos al usuario.
            }
            /*
                Según la documentación, es interesante que la actualización de los LiveData, se haga en el hilo principal.
                 */
            withContext(Dispatchers.Main) {//Con Dispatchers.Main, indicamos que el hilo se ejecute en el principal.
                if (user != null) {
                    //TODO hay que guardar todos los campos del usuario.
                    saveUserPreferents(user!!.id, user!!.name, user!!.email) //guardamos preferencias compartidas del usuario
                    Profile.profile.setUser(getUser())  //Me creo el Perfil del usuario
                    isLogginPreferencesLiveData.value = true  //notificamos a la ui
                }
                else
                    isLogginPreferencesLiveData.value = false  //notificamos a la ui
            }
        }
    }



    //Método que comprueba si existen las preferencias del usuario
    private fun isUserLoogedInShared(): Boolean {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)  //referenciamos nuestro fichero de pregerencias

        val id = sharedPreferences.getInt(context.getString(R.string.pref_user_id),-1)  //recupero el id
        val name = sharedPreferences.getString(context.getString(R.string.pref_user_name), null)
        val email = sharedPreferences.getString(context.getString(R.string.pref_user_email), null)

        return (id != -1 && name != null && email != null)
    }



    //Método que guarda las preferencias del usuario
    private fun saveUserPreferents( id: Int, name: String, email: String) {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)
        val editorPreferences = sharedPreferences.edit()  //editamos el fichero de preferencias compartidas para modificarlo

        editorPreferences.putInt(context.getString(R.string.pref_user_id), id)  //guardamos como entero el id del usuario
        editorPreferences.putString(context.getString(R.string.pref_user_name), name)
        editorPreferences.putString(context.getString(R.string.pref_user_email), email)
        editorPreferences.apply()
    }



    //Método que recupera las preferencias del usuario
    private fun getUser(): User {
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_user_file), Context.MODE_PRIVATE)  //referenciamos nuestro fichero de pregerencias

        val id = sharedPreferences.getInt(context.getString(R.string.pref_user_id),0)  //recupero el id
        val name = sharedPreferences.getString(context.getString(R.string.pref_user_name), context.getString(R.string.default_user_name))
        val email = sharedPreferences.getString(context.getString(R.string.pref_user_email), context.getString(R.string.default_user_email))
        return User(id, name!!, email!!, "", "", "")
    }



    //Método que registra un usuario
    fun register(user : User){
        var isReg = false

        //Aquí es cuando invocamos a la data para su persistencia.
        viewModelScope.launch(Dispatchers.IO) {
            isReg  = useCaseRegisterLogin.register(user)  //registramos el usaurio
            withContext(Dispatchers.Main) {
                register.value = isReg
            }
        }
    }


    fun showUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            var data: MutableList<User>? = null

            if (ListUsers.list.users.isEmpty())
            //todo    data = getAllUsersDatabaseUseCase()  //recupera todos los usuarios de la BBDD
            else
                data = ListUsers.list.users

            data.let {
                withContext(Dispatchers.Main) {
                    usersLiveData.value = it
                }
            }
        }
    }

    /*fun addUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            //todo newUserUseCase.setUser(user)
            var pos = 0
            //todo pos  = newUserUseCase()
             if ( pos != -1){
                /*
                Devuelve -1, si se cumplen las condiciones de insertar un nuevo usuario

                Lo que hacemos es al insertar un nuevo usuario, de la última posición del scroll (ultimo usuario)
                hacemos un desplazamiento de 20 para que veamos el nuevo pueblo.

               Según la documentación, es interesante que la actualización de los LiveData, se haga en el hilo principal.
                */
                withContext(Dispatchers.Main) {//Con Dispatchers.Main, indicamos que el hilo se ejecute en el principal.
                    posNewUserlLiveData.value = pos
                }
                showUsers()
            }




            fun deleteUser(pos : Int){
                //todo deleteUserUseCase.setPos(pos)
                //todo deleteUserUseCase()
                posDeleteHotelLiveDate.value = pos
                showUsers()
            }
*/

    fun getUserForPosition(pos: Int) : User ?{
        //todo getHUserForPosUseCase.setPos(pos)
        val user =null
        //todo user = getHotelForPosUseCase()
        return user
    }

}









