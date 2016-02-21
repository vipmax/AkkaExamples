#!/usr/bin/env bash
!/bin/sh

if [ ! -f ~/.ssh/id_rsa_vagrant.pub ]; then
    cp /home/vagrant/project/vms/sshkey/id_rsa.pub /home/vagrant/.ssh/id_rsa.pub;
    cp /home/vagrant/project/vms/sshkey/id_rsa /home/vagrant/.ssh/id_rsa;
    sudo chown vagrant /home/vagrant/.ssh/id_rsa
    sudo chown vagrant /home/vagrant/.ssh/id_rsa.pub
    cat /home/vagrant/project/vms/sshkey/id_rsa.pub >> /home/vagrant/.ssh/authorized_keys
    echo 'The key from sshkey has been succesfully installed'
else
    echo 'The key from sshkey is already installed'
fi

