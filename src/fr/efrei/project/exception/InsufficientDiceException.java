package fr.efrei.project.exception;

public class InsufficientDiceException extends Exception {

    public InsufficientDiceException()
    {
        super("Le nombre de dés est insuffisant pour cette taille de carte");
    }
}
