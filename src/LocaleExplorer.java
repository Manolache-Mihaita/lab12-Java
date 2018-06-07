import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.util.*;


public class LocaleExplorer {
    private final String baseName = "resources.Messages";
    private Locale locale;
    private ResourceBundle resourceBundle;

    public void run() {
        setLocale("en-US");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            message("prompt");
            String command = scanner.nextLine();
            if(command.equals("exit")) {
                break;
            }
            String[] params = command.trim().split("\\s+");
            switch (params[0]) {
                case "locales" : displayLocales(); break;
                case "set"     : setLocale(params[1]); break;
                case "info"    : localeInfo(); break;
                default        : message("invalid");
            }
        }

    }

    private void message(String key, String ...arguments) {
        String pattern = resourceBundle.getString(key);
        String message = new MessageFormat(pattern).format(arguments);
        System.out.println(message);
    }

    private void setLocale(String languageTag) {
        this.locale = Locale.forLanguageTag(languageTag);
        this.resourceBundle = ResourceBundle.getBundle(baseName, locale);
        message("locale.set", languageTag);
    }

    private void displayLocales() {
        Locale[] availableLocales = Locale.getAvailableLocales();

        StringBuilder locales = null;

        int i = 0;
        for(; i< availableLocales.length; ++i) {
            String country = availableLocales[i].getDisplayCountry();
            if(!country.isEmpty()) {
                locales = new StringBuilder(country);
                break;
            }
        }

        for(; i < availableLocales.length; ++i){
            String country = availableLocales[i].getDisplayCountry();
            if (!country.isEmpty())
                locales.append(", ").append(country);
        }
        System.out.println(locales.toString());
        message("locales", locales.toString());

    }

    private void localeInfo() {
        StringBuilder info = new StringBuilder("\n");
        info.append("Country: ")
                .append(this.locale.getDisplayCountry())
                .append("\n");
        info.append("Language: ")
                .append(this.locale.getDisplayLanguage())
                .append("\n");
//        info.append("Currency: ")
//                .append(Currency.getInstance(this.locale).getCurrencyCode())
//                .append("\n");
        info.append("Week days: ")
                .append(Arrays.toString(DateFormatSymbols.getInstance(this.locale).getWeekdays()))
                .append("\n");
        info.append("Months: ")
                .append(Arrays.toString(DateFormatSymbols.getInstance(this.locale).getMonths()))
                .append("\n");
        info.append("Today: ")
                .append((DateFormat.getDateInstance(DateFormat.LONG, this.locale)).format(new Date()))
                .append("\n");
        info.append("Country code ")
                .append(this.locale.getISO3Country())
                .append("\n");

        System.out.println(this.locale.getCountry()+"\n"+ info.toString());
        message("info",this.locale.getCountry(), info.toString());
    }
    public static void main(String args[]) {
        Collection <String> a;
        a=null ;
        a.add ( "s" );
        new LocaleExplorer().run();
    }
}
