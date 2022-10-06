#!/bin/bash

echo -e "=====  Nodes  ====" > /root/odf-resources.log
echo -e "\n $ oc get nodes \n" >> /root/odf-resources.log
oc get nodes >> /root/odf-resources.log

echo -e "\n =====  ClusterVersion  ====" >> /root/odf-resources.log
echo -e "\n $ oc get clusterversion \n" >> /root/odf-resources.log
oc get clusterversion >> /root/odf-resources.log

echo -e "\n =====  CSVs in all namespaces  ====" >> /root/odf-resources.log
echo -e "\n $ oc get csv -A \n" >> /root/odf-resources.log
oc get csv -A >> /root/odf-resources.log

odf_csv=`oc get csv -n openshift-storage |grep odf-operator | awk {'print $1'}`

echo -e "\n =====  Full version of ODF Operator Build  ====" >> /root/odf-resources.log
echo -e "\n $ oc get csv $odf_csv -n openshift-storage -o yaml |grep full_version \n" >> /root/odf-resources.log
oc get csv $odf_csv -n openshift-storage -o yaml |grep full_version >> /root/odf-resources.log

echo -e "\n =====  Pods in openshift-local-storage namespace  ====" >> /root/odf-resources.log
echo -e "\n $ oc get pods -n openshift-local-storage \n" >> /root/odf-resources.log
oc get pods -n openshift-local-storage >> /root/odf-resources.log

echo -e "\n =====  LocalVolumeSet  ====" >> /root/odf-resources.log
echo -e "\n $ oc get localvolumeset -n openshift-local-storage \n" >> /root/odf-resources.log
oc get localvolumeset -n openshift-local-storage >> /root/odf-resources.log

echo -e "\n =====  Persistent Volumes(PV)  ====" >> /root/odf-resources.log
echo -e "\n $ oc get pv \n" >> /root/odf-resources.log
oc get pv >> /root/odf-resources.log

echo -e "\n =====  Pods in openshift-storage namespace  ====" >> /root/odf-resources.log
echo -e "\n $ oc get pods -n openshift-storage \n" >> /root/odf-resources.log
oc get pods -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  Persistent Volume Claims(PVC)  ====" >> /root/odf-resources.log
echo -e "\n $ oc get pvc -n openshift-storage \n" >> /root/odf-resources.log
oc get pvc -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  StorageClasses  ====" >> /root/odf-resources.log
echo -e "\n $ oc get sc -n openshift-storage \n" >> /root/odf-resources.log
oc get sc -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  StorageCluster  ====" >> /root/odf-resources.log
echo -e "\n $ oc get storagecluster -n openshift-storage \n" >> /root/odf-resources.log
oc get storagecluster -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  CephCluster  ====" >> /root/odf-resources.log
echo -e "\n $ oc get cephcluster -n openshift-storage \n" >> /root/odf-resources.log
oc get cephcluster -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  StorageSystem  ====" >> /root/odf-resources.log
echo -e "\n $ oc get storagesystem -n openshift-storage \n" >> /root/odf-resources.log
oc get storagesystem -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  StorageCluster YAML  ====" >> /root/odf-resources.log
echo -e "\n $ oc get storagecluster -n openshift-storage -o yaml \n" >> /root/odf-resources.log
oc get storagecluster -n openshift-storage -o yaml >> /root/odf-resources.log

echo -e "\n =====  BackingStore  ====" >> /root/odf-resources.log
echo -e "\n $ oc get backingstore -n openshift-storage \n" >> /root/odf-resources.log
oc get backingstore -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  BucketClass  ====" >> /root/odf-resources.log
echo -e "\n $ oc get bucketclass -n openshift-storage \n" >> /root/odf-resources.log
oc get bucketclass -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  Noobaa  ====" >> /root/odf-resources.log
echo -e "\n $ oc get noobaa -n openshift-storage \n" >> /root/odf-resources.log
oc get noobaa -n openshift-storage >> /root/odf-resources.log

echo -e "\n =====  Noobaa YAML  ====" >> /root/odf-resources.log
echo -e "\n $ oc get noobaa -n openshift-storage -o yaml \n" >> /root/odf-resources.log
oc get noobaa -n openshift-storage -o yaml >> /root/odf-resources.log
