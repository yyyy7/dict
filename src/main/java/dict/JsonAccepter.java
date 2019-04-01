package dict;

public class JsonAccepter {

  public String errorCode;
  public String query;
  public String[] translation;
  public Basic basic;
  public Web[] web;
  public Dict dict;
  public Webdict webdict;
  public String l; 
  public String tSpeakUrl;
  public String speakUrl;

  static class Basic {
    public String phonetic;
    public String ukphonetic;
    public String usphonetic;
    public String ukspeech;
    public String usspeech;
    public String[] explains; 
  }

  static class Web {
    public String key;
    public String[] value;
  }

  static class Dict {
    public String url;
  }

  static class Webdict {
    public String url;
  }


}

