package com.guild.kaapoera.Util;

import com.google.firebase.auth.FirebaseAuth;

//Configurações de BD Firebase
public class Config_Bd {
    private static FirebaseAuth auth;
    public static FirebaseAuth AutenticacaoFirebase(){
        if(auth==null){
            auth=FirebaseAuth.getInstance();
                    }
        return auth;
    }
}
