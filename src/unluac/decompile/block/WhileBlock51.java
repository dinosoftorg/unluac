package unluac.decompile.block;

import unluac.decompile.CloseType;
import unluac.decompile.condition.Condition;
import unluac.parse.LFunction;

public class WhileBlock51 extends WhileBlock {

  private final int unprotectedTarget;
  
  public WhileBlock51(LFunction function, Condition cond, int begin, int end, int unprotectedTarget) {
    super(function, cond, begin, end, -1);
    this.unprotectedTarget = unprotectedTarget;
  }
  
  @Override
  public int scopeEnd() {
    return end - 2;
  }
  
  @Override
  public boolean isUnprotected() {
    return true;
  }
  
  @Override
  public int getUnprotectedLine() {
    return end - 1;
  }
  
  @Override
  public int getUnprotectedTarget() {
    return unprotectedTarget;
  };
  
  @Override
  public boolean isSplitable() {
    return cond.isSplitable();
  }
  
  @Override
  public Block[] split(int line, CloseType closeType) {
    Condition[] conds = cond.split();
    cond = conds[0];
    return new Block[] {
      new IfThenElseBlock(function, conds[1], begin, line + 1, end - 1, closeType, line - 1),
      new ElseEndBlock(function, line + 1, end - 1, CloseType.NONE, -1),
    };
  }
  
}
