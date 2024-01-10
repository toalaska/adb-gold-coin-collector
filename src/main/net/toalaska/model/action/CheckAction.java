package net.toalaska.model.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.toalaska.model.base.BaseModel;
@AllArgsConstructor
@Data
public abstract class CheckAction {




   public abstract    boolean run() throws Exception ;
   public abstract    void onSuccess() throws Exception ;
}
