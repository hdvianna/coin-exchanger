package hdvianna.coinexchanger.interaction.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
@RequiredArgsConstructor
public class ChangeShellClient {
    private final ChangeShellAdapter changeShellAdapter;
    @ShellMethod(value="Change bill for coins.\nType the a list of bills along with its amounts (change <bill> <amount> ...).\nExample: change 100 2 20 4\nIt will change 2 bills of 100 and 4 bills of 20 for coins")
    public String change(@ShellOption(arity = 100) int[] bills) {
        return changeShellAdapter.change(bills);
    }
}
