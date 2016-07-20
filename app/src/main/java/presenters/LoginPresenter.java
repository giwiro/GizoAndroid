package presenters;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import components.DaggerLoginComponent;
import components.LoginComponent;
import modules.PreferencesEditorModule;
import modules.PreferencesModule;
import rest.RestAdapter;
import rest.models.Usuario;
import rest.services.LoginService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ui.activities.Colecciones;
import ui.ghosts.LoginView;
import utils.ColeccionHelper;
import utils.SessionHelper;

/**
 * Created by Gi Wah Davalos on 15/05/2016.
 */
public class LoginPresenter {

    private LoginView loginView;
    private LoginService loginService;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;

        LoginComponent component
                = DaggerLoginComponent
                    .builder()
                    .preferencesModule(new PreferencesModule(((Activity) loginView).getApplicationContext()))
                    .preferencesEditorModule(new PreferencesEditorModule(((Activity) loginView).getApplicationContext()))
                    .build();

        pref = component.providePreferences();
        editor = component.provideSharedPrefsEditor();

        loginService = RestAdapter.getInstance().create(LoginService.class);

        /*Gson gson = new Gson();
        String serialized_user = pref.getString("usuario", "");

        Log.e("serialized_user", "<-" + serialized_user );*/
    }

    public void executeLogin(String email_txt, String password_txt) {
        if (!loginView.canSubmit()) {
            loginView.enableElements();
            return;
        }
        loginService
                .login(email_txt, password_txt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Usuario>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        try {
                            loginView.enableElements();
                            Toast.makeText((Activity) loginView, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("ERROR", e.getMessage());
                        }catch(Throwable ex) {
                            // Log the exception
                            Log.e("ERROR", ex.getMessage());
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(Usuario usuario) {
                        //enableElements();

                        SessionHelper.writeSession(editor, usuario);

                        ColeccionHelper.writeColecciones(editor, usuario.getColecciones());

                        Toast.makeText((Activity)loginView, "Autenticación correcta",
                                Toast.LENGTH_LONG).show();

                        Intent goToA = new Intent( (Activity)loginView , Colecciones.class);
                        goToA.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ((Activity)loginView).startActivity(goToA);
                        ((Activity)loginView).finish();

                        Log.e("USUARIO", usuario.toString());
                    }
                });
    }

}
