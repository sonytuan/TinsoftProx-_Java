

TinsoftProxy myProxy;
// main action

if(myProxy==null) {
   myProxy = new TinsoftProxy("",0);
}
myProxy.api_key = "";// input String apikey
myProxy.location = 0; //input int Location(0 for random)

String proxy="";

while(proxy==""){
  if(myProxy.isGetIPSuccess){
    proxy = myProxy.proxy;
  }else{
    String error = myProxy.error;
    System.out.println(error);
  }
  Thread.sleep(10000);
}

