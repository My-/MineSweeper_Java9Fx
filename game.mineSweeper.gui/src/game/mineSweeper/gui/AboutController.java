package game.mineSweeper.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable{
    @FXML public WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        webView = new WebView();
//        WebEngine webEngine = webView.getEngine();
////        webEngine.load(this.getClass().getResource("about.html").toString());
//        webEngine.load("http://google.com");
////        System.out.printf(this.getClass().getResource("about.html").toString());
    }
}
