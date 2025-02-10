module com.example.localcalendarjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;

    opens com.example.localcalendarjavafx to javafx.fxml;
    exports com.example.localcalendarjavafx;
}