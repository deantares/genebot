#!/usr/bin/perl

my %almacen;

my $entrada = "salida.txt";
my $anterior;
my $masanterior;
my $primera; 
my $bool = "1";

open (ENTRADA,"<$entrada") || die "ERROR: No puedo abrir el fichero $entrada\n";

while ($linea=<>){
		chop($linea);

		if($bool=="1"){
			$primera = $linea;
			$bool = "0";
		}

		
	#@procesa = split(/ /,$linea);
	if ($linea=~/Player 1 Wins!/){
		print "Resultado:";
		print $linea;
		print ":";
		print $anterior;
		print ":";
		print $masanterior;
		print ":Primmera:";
		print $primera;
		print "\n";
		$bool = "1";
		
	}
		$masanterior = $anterior;
		$anterior= $linea;
		
}