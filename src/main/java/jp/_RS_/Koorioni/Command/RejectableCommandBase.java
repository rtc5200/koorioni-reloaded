package jp._RS_.Koorioni.Command;

import org.bukkit.command.CommandSender;

public abstract class RejectableCommandBase extends ConsoleReject implements CommandBase {
	public void reject(CommandSender sender,RejectReason reason)
	{
		if(reason.equals(RejectReason.NotEnoughArgs))sender.sendMessage("引数が足りません。");
		if(reason.equals(RejectReason.TooMuchArgs))sender.sendMessage("引数が多すぎます。");
		if(reason.equals(RejectReason.NotAllowed))sender.sendMessage("権限設定を確認してください。");
		if(reason.equals(RejectReason.UnknownError))sender.sendMessage("不明なエラーが発生しました。");
		if(reason.equals(RejectReason.InvalidArgs))sender.sendMessage("不正な引数です。");
	}

}
