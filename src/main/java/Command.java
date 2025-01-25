public abstract class Command {

    public abstract void execute(UI ui, Storage storage, TaskList taskList);

    public boolean isBye() {
        return false;
    }
}
