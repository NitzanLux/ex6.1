package fileProcessor.scopePackage;

import fileProcessor.variblePackage.Variable;

import java.util.HashMap;

public class ConditionScope extends Scope{

    ConditionScope(HashMap variables, Scope father){
        super(variables, father);
    }

    @Override
    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}
///////////////     //////      ////////         //////
      ///         ///    ///    ///    ///     ///    ///
      ///         ///    ///    ///     ///    ///    ///
      ///         ///    ///    ///     ///    ///    ///
      ///         ///    ///    ///    ///     ///    ///
      ///           //////       ////////        //////