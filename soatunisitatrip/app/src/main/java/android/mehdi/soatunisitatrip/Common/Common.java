package android.mehdi.soatunisitatrip.Common;

import android.mehdi.soatunisitatrip.Remote.IMyAPI;
import android.mehdi.soatunisitatrip.Remote.RetrofitClient;

public class Common {
    public  static final String Base_url="http://192.168.1.5/tunisiatrip/";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(Base_url).create(IMyAPI.class);

    }
}
