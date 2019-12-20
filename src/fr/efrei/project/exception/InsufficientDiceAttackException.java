package fr.efrei.project.exception;

public class InsufficientDiceAttackException extends Exception {

    public InsufficientDiceAttackException()
    {
        super("Le territoire attaquant doit posséder au moins 2 dés");
    }

}
