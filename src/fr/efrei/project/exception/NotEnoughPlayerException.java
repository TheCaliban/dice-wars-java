package fr.efrei.project.exception;

public class NotEnoughPlayerException extends Exception {

    public NotEnoughPlayerException()
    {
        super("Il n'y a pas assez de joueur pour commencer à jouer");
    }

}
