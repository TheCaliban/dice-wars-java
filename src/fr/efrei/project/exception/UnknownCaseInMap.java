package fr.efrei.project.exception;

public class UnknownCaseInMap extends Exception {

    public UnknownCaseInMap()
    {
        super("Cette case n'appartient pas à la carte");
    }

}
