package uz.ictschool.handybook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.handybook.R
import uz.ictschool.handybook.api.APIClient
import uz.ictschool.handybook.api.APIService
import uz.ictschool.handybook.data.LoginDetails
import uz.ictschool.handybook.data.UserToken
import uz.ictschool.handybook.databinding.FragmentSignInBinding
import uz.ictschool.handybook.services.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignInFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSignInBinding
    private var api =APIClient.getInstance().create(APIService::class.java)
//    private lateinit var helper: Helper



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        var myShared = SharedPreference.newInstance(requireContext())

        binding.register.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main, SignUpFragment())
                .addToBackStack("SignIN").commit()
        }

        val api = APIClient.getInstance().create(APIService::class.java)




        binding.continueBtn.setOnClickListener {
            val user =
                LoginDetails(username = binding.loginOrg.text.toString().trim(),password =  binding.passwordOrg.text.toString().trim())

            if (binding.loginOrg.text.toString().isNotEmpty() && binding.passwordOrg.text.toString().isNotEmpty()){
                api.login(user).enqueue(object : Callback<UserToken> {
                    override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {
                        Log.d("TAG", "onResponse: ${response.body().toString()+user}")
                        if (response.isSuccessful){
                            val list = mutableListOf<UserToken>()
                            list.add(response.body()!!)
                            myShared.setLoginData(list)
                            parentFragmentManager.beginTransaction().replace(R.id.main,HomeFragment()).commit()
                        }
                        else{
                            Toast.makeText(requireContext(), "password or username is incorrect ! please try again", Toast.LENGTH_SHORT).show()

                        }}

                    override fun onFailure(call: Call<UserToken>, t: Throwable) {
                        Log.d("TAG", "onFailure: $t")

                    }

                })
            }

            else Toast.makeText(requireContext(), "username or password field cannot be empty", Toast.LENGTH_SHORT).show()

        }


//        helper =Helper.getInstance(requireContext())


//
//        setLoginButton()
//        setBackButton()
        return binding.root
    }


//    private fun setBackButton() {
//        binding.continueBtn.setOnClickListener {
//            findNavController().popBackStack()
//        }
//    }

//    private fun setLoginButton() {
//        binding.loginLoginMb.setOnClickListener {
//            val signIn = SignIn(
//                binding.loginUsernameEditAcet.text.toString().trim(),
//                binding.loginPasswordEditAcet.text.toString().trim()
//            )
//            if (signIn.password == "" || signIn.username == "") return@setOnClickListener
//            if (!validate(signIn)) return@setOnClickListener
//            api.login(signIn).enqueue(object: Callback<User> {
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (!response.isSuccessful) {
//                        binding.loginPasswordEditAcet.setText("")
//                        Toast.makeText(requireContext(), "Noto'g'ri username yoki parol", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//                    val user: User = response.body()!!
//                    if (binding.loginRememberMeAcchb.isChecked) shared.setRememberMe(user.username) else shared.setRememberMe("")
//                    shared.setUser(user)
//                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
//                }
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    Log.d("TAG", "$t")
//                }
//
//            })
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}