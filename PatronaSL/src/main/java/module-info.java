module es.iesfranciscodelosrios.controllers {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires transitive java.xml.bind;
	requires transitive javafx.graphics;
	requires transitive java.sql;
	requires transitive javafx.base;
	requires transitive java.desktop;
    
    opens es.iesfranciscodelosrios.controllers to javafx.fxml;
    opens es.iesfranciscodelosrios.model to javafx.fxml;
    opens es.iesfranciscodelosrios.interfaces to javafx.fxml;
    opens es.iesfranciscodelosrios.utils to java.xml.bind;
    exports es.iesfranciscodelosrios.controllers;
    exports es.iesfranciscodelosrios.model;
    exports es.iesfranciscodelosrios.interfaces;
}
