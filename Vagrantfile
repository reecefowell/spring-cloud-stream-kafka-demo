Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"

  config.vm.network "private_network", type: "dhcp"

  config.vm.define "kafkademo" do |kafkademo|
    kafkademo.vm.hostname = "reecefowell-kafka-demo"

    kafkademo.vm.provider "virtualbox" do |v|
      v.name = "reecefowell-kafka-demo"
      v.memory = 2048
    end

    kafkademo.vm.provision "ansible" do |ansible|
      ansible.limit = "all,localhost"
      ansible.playbook = "ansible/vagrant.yml"
      ansible.groups = {
          "common" => ["kafkademo"],
          "java" => ["kafkademo"],
          "zookeeper" => ["kafkademo"],
          "kafka" => ["kafkademo"]
      }
    end
  end
end
