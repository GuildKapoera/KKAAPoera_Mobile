package com.guild.kaapoera.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Salvar o usuario autenticado para nao precisar logar sempre
public class UsuarioAuteticado{
    public static FirebaseUser UsuarioLogado(){
        FirebaseAuth usuario = Config_Bd.AutenticacaoFirebase();
        return usuario.getCurrentUser();
    }
}