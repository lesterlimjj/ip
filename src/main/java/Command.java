public abstract class Command {

    public abstract void execute(Ui ui, Storage storage, TaskList taskList);

    public boolean isBye() {
        return false;
    }
}
