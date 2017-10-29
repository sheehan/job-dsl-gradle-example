# install node/grunt if necessary
node_version=0.10.26
node_name=node-v${node_version}-linux-x64
node_dir=~/jenkins/node

mkdir -p $node_dir

test -f ${node_dir}/${node_name}.tar.gz ||  wget -P $node_dir http://nodejs.org/dist/v${node_version}/${node_name}.tar.gz
test -f ${node_dir}/${node_name}/bin/node || tar -zxvf ${node_dir}/${node_name}.tar.gz  -C $node_dir

export PATH=${node_dir}/${node_name}/bin:$PATH

npm update
npm install
npm install grunt
npm install grunt-cli

export PATH=$PATH:node_modules/grunt-cli/bin/

grunt test