public class HowToUse {
    public static void main(String[] args) {
        String tinsoftToken = "{your-token}";

        // get current ip
        TinsoftProxy tinsoftProxy = new TinsoftProxy(tinsoftToken);
        tinsoftProxy.getCurrentIP();

        if(tinsoftProxy.getProxy() == null){
            System.err.println(tinsoftProxy.getError());
        } else {
            System.out.println(tinsoftProxy.getProxy());
        }

        // change ip
        tinsoftProxy.changeIP();
        if(tinsoftProxy.getProxy() == null){
            System.err.println(tinsoftProxy.getError());
        } else {
            System.out.println(tinsoftProxy.getProxy());
        }
    }
}
