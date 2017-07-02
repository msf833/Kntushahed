package ir.madamas.kntushahed.kntushahed;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NFP_7037 on 7/2/2017.
 */

public class FirebaseInstanceIdServiceClass extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        //Get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.i("New refreshedToken: ", refreshedToken);

        //You can save the token into third party server to do anything youwant
    }
}
