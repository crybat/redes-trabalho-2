#!/usr/bin/env bash
sudo ipfw flush

echo ""
echo "Rb: "         $1
echo "Tprop: "      $2
echo "PLR (c->s): " $3
echo "PLR (s->c): " $4
echo ""

sudo ipfw add pipe 1 all from 127.0.0.1 to 127.0.0.2 out
sudo ipfw add pipe 2 all from 127.0.0.2 to 127.0.0.1 out
sudo ipfw pipe 1 config bw $1bps delay $2ms plr $3 
sudo ipfw pipe 2 config bw $1bps delay $2ms plr $4

sudo ipfw show
sudo ipfw pipe show
