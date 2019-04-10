package android.mehdi.soatunisitatrip.Common;

import android.mehdi.soatunisitatrip.Remote.IMyAPI;
import android.mehdi.soatunisitatrip.Remote.RetrofitClient;

public class Common {
    public  static final String Base_url="http://41.226.11.252:1180/tunisiatrip/";

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(Base_url).create(IMyAPI.class);

    }
}
