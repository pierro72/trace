package com.ex.trace.service.mapper.mobile;

import com.ex.trace.domaine.*;
import com.ex.trace.domaine.security.Utilisateur;

public final class MapperUtil {

    public static boolean estProprietaire ( Message message, Utilisateur utilisateur){
        return message.getAutheur().getId().longValue() == utilisateur.getId().longValue();
    }

    public static boolean estLike ( Message message, Utilisateur utilisateur){
        boolean estLike = false;
        for ( Recommandation recommandation : message.getRecommandations()){
            if ( recommandation.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estLike = true;
                break;
            }
        }
        return estLike;
    }

    public static boolean estSignale ( Message message, Utilisateur utilisateur){
        boolean estSignale = false;
        for ( Signalement signalement : message.getSignalements()){
            if ( signalement.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estSignale = true;
                break;
            }
        }
        return estSignale;
    }

    public static boolean estVue ( Trace trace, Long id){
        boolean estVue = false;
        for ( Visite v : trace.getVisites()) {
            if ( v.getUtilisateur().getId().longValue() == id.longValue() ){
                estVue = true;
                break;
            }
        }
        return estVue;
    }

}
