package dict;

public class JsonAccepter {

  public String errorCode;
  public String query;
  public String[] translation;

  class basic {
    public String phonetic;
    public String ukphonetic;
    public String usphonetic;
    public String ukspeech;
    public String usspeech;
    public String[] explains; 
  }

  Web[] web;
  class Web {
    public String key;
    public String[] value;
  }

  class dict {
    public String url;
  }

  class webdict {
    public String url;
  }

  public String l; 
  public String tSpeakUrl;
  public String speakUrl;

}

