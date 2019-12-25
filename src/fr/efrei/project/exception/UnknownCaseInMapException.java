package fr.efrei.project.exception;

public class UnknownCaseInMapException extends Exception {

    public UnknownCaseInMapException()
    {
        super("Cette case n'appartient pas à la carte");
    }

}
