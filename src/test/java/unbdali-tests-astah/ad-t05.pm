dtmc

module Teste5
	sTeste5 : [-1..4] init 0;

	[] sTeste5=0 -> 1.0:(sTeste5'=1);
	[A0_initial] sTeste5=1 -> 0.5:(sTeste5'=3) + 0.5:(sTeste5'=4);
	[A0_final] sTeste5=2 -> 1.0:(sTeste5'=2);
	[] sTeste5=3 -> 1.0:(sTeste5'=2);
	[] sTeste5=4 -> 1.0:(sTeste5'=2);
	[A0_fail] sTeste5=-1 -> 1.0:(sTeste5'=-1);
endmodule
